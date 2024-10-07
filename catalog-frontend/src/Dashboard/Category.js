import React from 'react';
import {
  Card,
  CardHeader,
  CardTitle,
  CardBody
} from '@patternfly/react-core';

import coffeesIcon from '../images/category/coffees.png'
import candiesIcon from '../images/category/candies.png'
import cheeseIcon from '../images/category/cheese.png'
import relishesIcon from '../images/category/relishes.png'
import chickenIcon from '../images/category/chicken.png'
import crackersIcon from '../images/category/crackers.png'
import seafoodIcon from '../images/category/crank.png'
import rainsinsIcon from '../images/category/raisins.png'

function Category({category, onClick}) {

  const icons = {
    'candies' : candiesIcon,
    'cheese' : cheeseIcon,
    'coffees' : coffeesIcon,
    'chicken' : chickenIcon,
    'relishes' : relishesIcon,
    'crackers' : crackersIcon,
    'crank' : seafoodIcon,
    'raisins' : rainsinsIcon
  }

  const getImageUrl = (cat) => {
    let image = icons[cat.picture];
    return image;
  }

  return (
    <Card
      isSelectableRaised
      isHoverable
      key={category.id}
      id={'card-view'+category.id}
      onClick={onClick}
      >
        <CardHeader>
          <img
            className="avatar"
            src={getImageUrl(category)}
            alt={category.category_name}
            style={{height: '50px'}}
          />
        </CardHeader>
        <CardTitle>{category.category_name}</CardTitle>
        <CardBody>{category.description}</CardBody>
    </Card>
  );

}

export {Category};
