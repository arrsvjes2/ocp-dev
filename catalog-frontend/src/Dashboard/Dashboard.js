import * as React from 'react';
import {
  PageSection,
  PageSectionVariants,
  Title,
  Breadcrumb,
  BreadcrumbItem,
  Text,
  TextContent
} from '@patternfly/react-core';

import { Categories } from './Categories';

function Dashboard() {

  const DashboardBreadcrumb = (
    <Breadcrumb>
      <BreadcrumbItem>Catalog</BreadcrumbItem>
      <BreadcrumbItem to="#" isActive>Categories</BreadcrumbItem>
    </Breadcrumb>
  );

  return (
    <React.Fragment>
      <PageSection variant={PageSectionVariants.light}>
        {DashboardBreadcrumb}
        <TextContent>
          <Title headingLevel="h1" size="lg">Dashboard Page</Title>
            <Text component="p">Full list of categories</Text>
        </TextContent>
      </PageSection>
      <PageSection variant={PageSectionVariants.default} padding={{default: 'noPadding'}}>
        <Categories/>
      </PageSection>
    </React.Fragment>
  );
}


export {Dashboard};
