import React from "react";
import { Flex, FlexItem, Text } from '@patternfly/react-core';

function Product({product}) {

  return (
    <React.Fragment>
      <Text component="b">{product.product_name}</Text>
      <Flex>
        <FlexItem>
          <Text component="p">{product.quantityPerUnit}</Text>
        </FlexItem>
        <FlexItem align={{default:'alignRight'}}>
          <Text component="i">${product.unitPrice}</Text>
        </FlexItem>
      </Flex>
    </React.Fragment>
  );

}

export {Product};
