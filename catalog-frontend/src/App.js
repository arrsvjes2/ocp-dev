import React from 'react';
import { NavLink, useLocation, useHistory } from 'react-router-dom';
import {
  Page,
  PageHeader,
  PageSidebar,
  SkipToContent,
  Nav,
  NavList,
  NavItem,
} from '@patternfly/react-core';

import accessibleStyles from "@patternfly/react-styles/css/utilities/Accessibility/accessibility";
import spacingStyles from "@patternfly/react-styles/css/utilities/Spacing/spacing";
import { css } from "@patternfly/react-styles";
import { BellIcon, CogIcon } from "@patternfly/react-icons";

//This imports are must to render css
import "@patternfly/react-core/dist/styles/base.css";
import "@patternfly/patternfly/patternfly.css";

import logo from './logo.svg';
import './App.css';
import { AppRoutes, routes } from './Routes';

function App({children}) {

  const [isNavOpen, setIsNavOpen] = React.useState(true);
  const [isMobileView, setIsMobileView] = React.useState(true);
  const [isNavOpenMobile, setIsNavOpenMobile] = React.useState(false);

  const onNavToggleMobile = () => {
    setIsNavOpenMobile(!isNavOpenMobile);
  };
  const onNavToggle = () => {
    setIsNavOpen(!isNavOpen);
  };

  function LogoImg() {
    const history = useHistory();
    function handleClick() {
      history.push('/');
    }
    return (
      <img src={logo} alt="React app" onClick={handleClick} width={'40px'} />
    );
  }

  const Header = (
    <PageHeader
      logo={<LogoImg />}
      showNavToggle
      isNavOpen={isNavOpen}
      onNavToggle={isMobileView ? onNavToggleMobile : onNavToggle}
    />
  );

  const location = useLocation();

  const Navigation = (
    // isActive={"/" === useLocation().pathname}
    <Nav id="nav-primary-simple" theme="dark">
      <NavList id="nav-list-simple">
        {routes.map( (route,idx) => { return (
          <NavItem key={route.label+'-'+idx} id={route.label+'-'+idx} isActive={route.path === location.pathname}>
            <NavLink exact={route.exact} to={route.path}>
              {route.label}
            </NavLink>
          </NavItem>
          )}
        )}
      </NavList>
    </Nav>
  );

  const Sidebar = (
    <PageSidebar
      theme="dark"
      nav={Navigation}
      isNavOpen={isMobileView ? isNavOpenMobile : isNavOpen} />
  );

  const pageId = 'primary-app-container';

  const PageSkipToContent = (
    <SkipToContent onClick={(event) => {
      event.preventDefault();
      const primaryContentContainer = document.getElementById(pageId);
      primaryContentContainer && primaryContentContainer.focus();
    }} href={`#${pageId}`}>
      Skip to Content
    </SkipToContent>
  );


  return (
      <React.Fragment>
        <Page
            mainContainerId={pageId}
            header={Header}
            sidebar={Sidebar}
            isManagedSidebar
            skipToContent={PageSkipToContent}
          >
            {children}
          </Page>
      </React.Fragment>
  );
}

export default App;
