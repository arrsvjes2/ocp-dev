import React from 'react';
import { useHistory } from 'react-router-dom';
import {
  Gallery, InputGroup, TextInput,
  Toolbar, ToolbarContent, ToolbarToggleGroup, ToolbarItem,
  Button, ButtonVariant,
  Divider, PageSection, Drawer, DrawerContent, DrawerContentBody
} from '@patternfly/react-core';
import FilterIcon from '@patternfly/react-icons/dist/esm/icons/filter-icon';
import SearchIcon from '@patternfly/react-icons/dist/esm/icons/search-icon';

import { apiCategory } from '../redux';
import { Category } from './Category';

function Categories() {

  const [inputValue, setInputValue] = React.useState('');

  const {data: categories} = apiCategory.endpoints.getCategories.useQuery(undefined);

  const navigate = useHistory();

  const onFilterChange = newValue => {
    setInputValue(newValue);
  };

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
    </React.Fragment>
  );

  const ToolbarItems = (
    <ToolbarToggleGroup toggleIcon={<FilterIcon />} breakpoint="xl">
      {toggleGroupItems}
    </ToolbarToggleGroup>
  );

  const onCategoryClick = category => {
    console.log('clicked : ', category);
    navigate.push('/products?category='+category.id, {category});
  }

  const filteredResp = () => {
    if (!categories) {
      return [];
    }
    let filter = inputValue.toLowerCase()
    if (null==filter || filter.length < 2) {
      return categories;
    } else {
      return categories.filter(category => {
        return category.category_name.toLowerCase().includes(filter) ||
          category.description.toLowerCase().includes(filter)
      });
    }
  }

  const filteredCategories = filteredResp().map((category, key) => {
    return ( <Category
        key={key}
        category={category}
        onClick={e => onCategoryClick(category)}
      />
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
        <Drawer isExpanded={true} className={'pf-m-inline-on-2xl'}>
          <DrawerContent className={'pf-m-no-background'}>
            <DrawerContentBody hasPadding>
              <Gallery hasGutter style={{ '--pf-l-gallery--GridTemplateColumns--min': '260px' }}>
              { filteredCategories }
              </Gallery>
            </DrawerContentBody>
          </DrawerContent>
        </Drawer>
      </PageSection>
    </React.Fragment>
  );

}

export {Categories};
