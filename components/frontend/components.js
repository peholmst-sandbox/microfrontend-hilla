/* This defines all the components that are made available for all modules */
const promises = [];
promises.push(import('@vaadin/app-layout'));
promises.push(import('@vaadin/app-layout/vaadin-drawer-toggle'));
promises.push(import('@vaadin/button'));
promises.push(import('@vaadin/charts'));
promises.push(import('@vaadin/icon'));
promises.push(import('@vaadin/icons'));
promises.push(import('@vaadin/notification'));
promises.push(import('@vaadin/scroller'));
promises.push(import('@vaadin/side-nav'));
promises.push(import('@vaadin/side-nav/vaadin-side-nav-item'));
promises.push(import('@vaadin/text-field'));

Promise.all(promises).then(() => console.log('Components bundle loaded'));
