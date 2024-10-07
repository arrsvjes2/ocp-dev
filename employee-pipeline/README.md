# Employee CI/CD pipeline

## Define Pipeline

### Create resources

```sh
oc apply -f employee-source.secret.yaml
```

```sh
export REGISTRY=registry.example.com
export REGISTRY_USER=reguser
export REGISTRY_PASSWD=xxxxx

oc create secret docker-registry \
    --docker-server=$REGISTRY \
    --docker-username=$REGISTRY_USER \
    --docker-password=$REGISTRY_PASSWD \
    --docker-email=unused \
    pull-secret

oc patch serviceaccount pipeline \
          -p '{"secrets": [{"name": "pull-secret"}]}'
```

### Create pipeline

```sh
oc apply -f employee-pipeline.pipeline.yaml
```

```sh
$ tkn pipeline list
NAME                AGE              LAST RUN                                   STARTED         DURATION   STATUS
employee-pipeline   42 minutes ago   -                                          -               -          -
```

## Run pipeline manually

```sh
tkn pipeline start employee-pipeline \
    -w name=shared-workspace,volumeClaimTemplateFile=$(pwd)/employee-source.pvc.yaml \
    --serviceaccount=pipeline \
    -p deployment-name=employee-backend \
    -p git-url=http://repo2.example.com:5000/finesmart/employee-backend.git \
    -p maven-repo=http://nexus2.example.com:8081/repository/maven-public/ \
    -p backend-image=image-registry.openshift-image-registry.svc:5000/turquoise/employee-backend:latest \
    --use-param-defaults
PipelineRun started: employee-pipeline-run-dq9ll

In order to track the PipelineRun progress run:
tkn pipelinerun logs employee-pipeline-run-dq9ll -f -n turquoise
```

Check the status of the pipelinerun

```sh
tkn pipelinerun list
NAME                          STARTED        DURATION   STATUS
employee-pipeline-run-grxln   1 minute ago   48s        Running
```

## Configure triggers

### Configure gogs

To allow gogs to send webhooks to sites that resolv to an internal network, add the following key to the configuration file :

```ini
# conf/app.ini
[security]
LOCAL_NETWORK_ALLOWLIST = 192.168.130.11, el-employee-backend-turquoise.apps-crc.testing
```

_NOTE:_ the container needs to be restarted

### Manifests

- create triggerbinding

- create triggertemplate

- create trigger

- create eventlistener

```sh
$ oc get pods
NAME                                                           READY   STATUS      RESTARTS   AGE
el-employee-backend-6cc5bcf944-57hf7                           1/1     Running     0          12m
$ oc get service
NAME                  TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)             AGE
el-employee-backend   ClusterIP   10.217.5.128   <none>        8080/TCP,9000/TCP   45s
$ oc expose svc el-employee-backend
route.route.openshift.io/el-employee-backend exposed
$ oc get routes
NAME                  HOST/PORT                                        PATH   SERVICES              PORT            TERMINATION   WILDCARD
el-employee-backend   el-employee-backend-turquoise.apps-crc.testing          el-employee-backend   http-listener                 None
```

```sh
echo -n '{"repository":{"clone-url":"http://repo2.example.com:5000/finesmart/employee-backend.git","name":"employee-backend"},"after":"main","action":"pushed"}' |  openssl sha256 -hmac 18d335681c6b7732d6611c7f428e0252d5a51d5c
```

```sh
curl -v -X POST http://el-employee-backend-turquoise.apps-crc.testing -H 'content-Type: application/json' -d '{"repository":{"clone-url":"http://repo2.example.com:5000/finesmart/employee-backend.git","name":"employee-backend"},"after":"main","action":"pushed"}' -H "X-Gogs-Event: push" -H "X-Gogs-Signature: sha256=407e05ce9b63ef23b8f17392ba6193fe01495a4610d3e1692a7838b182a594db"
< HTTP/1.1 202 Accepted
< content-type: application/json
< date: Thu, 29 Jun 2023 15:07:31 GMT
< content-length: 168

{"eventListener":"employee-backend","namespace":"turquoise","eventListenerUID":"69b38184-3bf4-4a5d-b078-7d7547e493bf","eventID":"273a1a88-5156-4916-a829-3e6538714427"}
```

The event reception is shown in the logs of the el-employee-backend pod :

```json
$ oc logs -f el-employee-backend-6cc5bcf944-57hf7
...
{"severity":"info","timestamp":"2023-06-30T05:53:06.940Z","logger":"eventlistener.event-broadcaster","caller":"record/event.go:285","message":"Event(v1.ObjectReference{Kind:\"EventListener\", Namespace:\"turquoise\", Name:\"employee-backend\", UID:\"\", APIVersion:\"v1beta1\", ResourceVersion:\"\", FieldPath:\"\"}): type: 'Normal' reason: 'dev.tekton.event.triggers.started.v1' ","commit":"2ec8bc6"}
{"severity":"info","timestamp":"2023-06-30T05:53:06.940Z","logger":"eventlistener","caller":"sink/sink.go:442","message":"ResolvedParams : [{Name:git-repo-url Value:http://repo2.example.com:5000/finesmart/employee-backend.git} {Name:git-repo-name Value:employee-backend} {Name:git-revision Value:42a234bea8abf604f5650b61dca095c4b32843eb}]","commit":"2ec8bc6","eventlistener":"employee-backend","namespace":"turquoise","/triggers-eventid":"d36e1d5a-3446-49cd-8ffe-138b15e4c9a9","eventlistenerUID":"69b38184-3bf4-4a5d-b078-7d7547e493bf","/triggers-eventid":"d36e1d5a-3446-49cd-8ffe-138b15e4c9a9","/trigger":"employee-backend-trigger"}
{"severity":"info","timestamp":"2023-06-30T05:53:06.946Z","logger":"eventlistener","caller":"resources/create.go:98","message":"Generating resource: kind: &APIResource{Name:pipelineruns,Namespaced:true,Kind:PipelineRun,Verbs:[delete deletecollection get list patch create update watch],ShortNames:[pr prs],SingularName:pipelinerun,Categories:[tekton tekton-pipelines],Group:tekton.dev,Version:v1beta1,StorageVersionHash:RcAKAgPYYoo=,}, name: employee-pipeline-employee-backend-","commit":"2ec8bc6"}
{"severity":"info","timestamp":"2023-06-30T05:53:06.946Z","logger":"eventlistener","caller":"resources/create.go:106","message":"For event ID \"d36e1d5a-3446-49cd-8ffe-138b15e4c9a9\" creating resource tekton.dev/v1beta1, Resource=pipelineruns","commit":"2ec8bc6"}
{"severity":"info","timestamp":"2023-06-30T05:53:06.964Z","logger":"eventlistener.event-broadcaster","caller":"record/event.go:285","message":"Event(v1.ObjectReference{Kind:\"EventListener\", Namespace:\"turquoise\", Name:\"employee-backend\", UID:\"69b38184-3bf4-4a5d-b078-7d7547e493bf\", APIVersion:\"triggers.tekton.dev/v1beta1\", ResourceVersion:\"2042080\", FieldPath:\"\"}): type: 'Normal' reason: 'dev.tekton.event.triggers.successful.v1' ","commit":"2ec8bc6"}
```
