import React from 'react';
import { useLocation } from 'react-router-dom';
import {
  PageSection,
  Title,
  Breadcrumb,
  BreadcrumbItem,
  Text,
  TextContent,
  PageSectionVariants
} from '@patternfly/react-core';

import { Products } from './Products';

function useQuery() {
  const { search } = useLocation();

  return React.useMemo(() => new URLSearchParams(search), [search]);
}

function ProductDashboard(props) {

  const query = useQuery();

  const state = props.location.state;
  const categoryId = query.get("category");

  const ProductsBreadcrumb = (
    <Breadcrumb>
      <BreadcrumbItem>Catalog</BreadcrumbItem>
      <BreadcrumbItem to="#" isActive>Products</BreadcrumbItem>
    </Breadcrumb>
  );

  return (
    <React.Fragment>
      <PageSection variant={PageSectionVariants.light}>
        {ProductsBreadcrumb}

        <TextContent>
          <Title headingLevel="h1" size="lg">Products Page</Title>
          { state && state.category ?
              <Text component="p">{state.category.category_name} products</Text> :
              <Text component="p">Full list of products</Text>
          }
        </TextContent>
      </PageSection>

      <PageSection variant={PageSectionVariants.default} padding={{default: 'noPadding'}}>

        <Products
            categoryId={categoryId}
          />

      </PageSection>
    </React.Fragment>
  );
}


export {ProductDashboard};
