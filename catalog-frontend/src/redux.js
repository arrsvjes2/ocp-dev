import { fetchBaseQuery, createApi } from '@reduxjs/toolkit/query/react';

export const serviceQuery = ({path}) => {
  const baseQuery = fetchBaseQuery({
    baseUrl: `${process.env.REACT_APP_CATALOG_API_URL}${path}`,
  });
  return async (args, api, extraOptions) => {
    let result = await baseQuery(args, api, extraOptions);
    console.log("result: " , result)
    return result;
  }
};

export const apiCategory = createApi({
  reducerPath: 'categories',
  baseQuery: serviceQuery({path: 'category'}),
  tagTypes: ['Category'],
  endpoints: builder => builder.query({
    getCategories: builder.query({
      query : () => '/',
      providesTag: ['Category']
    })
  })
});

export const apiProduct = createApi({
  reducerPath: 'products',
  baseQuery: serviceQuery({path: 'product'}),
  tagTypes: ['Product'],
  endpoints: builder => builder.query({
    getProducts: builder.query({
      query : (arg) => {
        const {category, page, size} = arg;
        console.log("arg:", arg)
        const params = category ? {size, page: page-1, category} : {size, page:page-1};
        return {
          url: '/',
          params : params
        }
      },
      providesTag: ['Product']
    }),
    countProducts: builder.query({
      query : (arg) => {
        const {category} = arg;
        // const params = category ? {category} : {};
        return {
          url: '/count',
          params : category ? {category} : {}
        }
      },
      providesTag: ['Count']
    }),
  })

});
