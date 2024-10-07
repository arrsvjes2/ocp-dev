import * as React from 'react';
import { Route, Switch } from 'react-router-dom';
import { Dashboard } from './Dashboard/Dashboard';
import { About } from './About/About';
import { NotFound } from './NotFound/NotFound';
import { ProductDashboard } from './Products/ProductDashboard';


const routes = [
  {
    component: Dashboard,
    exact: true,
    label: 'Dashboard',
    path: '/',
    title: 'Main Dashboard',
  },
  {
    component: ProductDashboard,
    exact: true,
    label: 'Products',
    path: '/products',
    title: 'Product List'
  },
  {
    component: About,
    exact: true,
    label: 'About',
    path: '/about',
    title: 'About Page',
  },
];

const PageNotFound = function (title) {
  return (
    <Route component={NotFound} />
  );
}

function AppRoutes () {
  return (
    <Switch>
      {routes.map((route, idx) =>
        <Route component={route.component} path={route.path} exact={route.exact} key={idx} title={route.title} />
      )}
      <PageNotFound title="404 Page Not Found" />
    </Switch>
  );
}

export {routes, AppRoutes};
