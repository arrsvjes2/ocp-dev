import React from 'react';
import {
  InputGroup, TextInput,
  Toolbar, ToolbarContent, ToolbarToggleGroup, ToolbarItem,
  Button, ButtonVariant,
  Divider, PageSection,
  DataList, DataListItem, DataListItemRow, DataListItemCells, DataListCell, Pagination
} from '@patternfly/react-core';
import FilterIcon from '@patternfly/react-icons/dist/esm/icons/filter-icon';
import SearchIcon from '@patternfly/react-icons/dist/esm/icons/search-icon';

import { apiProduct } from '../redux';
import { Product } from './Product';


function Products({categoryId}) {

  const [page, setPage] = React.useState(1);
  const [size, setSize] = React.useState(10);
  const [inputValue, setInputValue] = React.useState('');

  const {data: count} = apiProduct.endpoints.countProducts.useQuery({category: categoryId});
  const {data: products} = apiProduct.endpoints.getProducts.useQuery({category: categoryId, page, size});

  const onFilterChange = newValue => {
    setInputValue(newValue);
  };

  const onSetPage = (_event, newPage) => {
    setPage(newPage);
  };

  const onPerPageSelect = (_event, newPerPage, newPage) => {
    setSize(newPerPage);
    setPage(newPage);
  }

  const toggleGroupItems = (
    <React.Fragment>
      <ToolbarItem>
        <InputGroup>
          <TextInput
            name="filter"
            id="categoryFilter"
            type="search"
            aria-label="filter categories"
            onChange={onFilterChange}
            value={inputValue}
            />
          <Button variant={ButtonVariant.control} aria-label="filter button for filter categories">
            <SearchIcon />
          </Button>
        </InputGroup>
      </ToolbarItem>
      <ToolbarItem alignment={{default:'alignRight'}} id="toolbar-pagination">
        <Pagination
            perPageComponent="button"
            itemCount={count}
            page={page}
            perPage={size}
            onSetPage={onSetPage}
            onPerPageSelect={onPerPageSelect}
            widgetId="top-pagination"
          />
      </ToolbarItem>
    </React.Fragment>
  );

  const ToolbarItems = (
    <ToolbarToggleGroup toggleIcon={<FilterIcon />} breakpoint="xl">
      {toggleGroupItems}
    </ToolbarToggleGroup>
  );

  const filteredResp = () => {
    if (!products) {
      return [];
    }
    let filter = inputValue.toLowerCase()
    if (null==filter || filter.length < 2) {
      return products;
    } else {
      return products.filter(product => {
        return product.product_name.toLowerCase().includes(filter) ||
          product.categoryName.toLowerCase().includes(filter)
      });
    }
  }

  const filteredProducts = filteredResp().map((product, key) =>{
    return (
      <DataListItem
          aria-label={'data-list-item'+key}
          id={'data-list'+product.id}
          key={'data-list-key'+key}
        >
        <DataListItemRow>
          <DataListItemCells
              dataListCells={[
                <DataListCell
                  key={'data-list-cell'+key}
                  >
                    <Product
                      product={product}
                      />
                </DataListCell>
              ]}
            />
        </DataListItemRow>
      </DataListItem>
    )
  });

  return (
    <React.Fragment>

        <Divider component="div" />
        <Toolbar id="full-page-data-toolbar" usePageInsets>
          <ToolbarContent>{ToolbarItems}</ToolbarContent>
        </Toolbar>
        <Divider component="div" />

      <PageSection isFilled padding={{default: 'noPadding'}}>
        <DataList
          aria-label='product list'>
          {filteredProducts}
        </DataList>
      </PageSection>

    </React.Fragment>
  );

}

export {Products};
