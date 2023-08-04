// TODO These imports will have to be handled through a component bundle

import '@vaadin/app-layout';
import '@vaadin/app-layout/vaadin-drawer-toggle';
import '@vaadin/icon';
import '@vaadin/icons';
import '@vaadin/scroller';
import '@vaadin/side-nav';
import '@vaadin/side-nav/vaadin-side-nav-item';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset';

import { appStore } from '../stores/app-store';
import { html } from 'lit';
import { customElement } from 'lit/decorators.js';
import { Layout } from './view';

interface RouteInfo {
    path: string;
    title: string;
    icon: string;
  }

@customElement('main-layout')
export class MainLayout extends Layout {
    render() {
        return html`
            <vaadin-app-layout primary-section="drawer">
                <header slot="drawer">
                    <h1 class="text-l m-0">${appStore.applicationName}</h1>
                </header>
                <vaadin-scroller slot="drawer" scroll-direction="vertical">
                    <vaadin-side-nav aria-label="${appStore.applicationName}">
                        ${this.getMenuRoutes().map(
                            (viewRoute) => html`
                                <vaadin-side-nav-item path=${viewRoute.path}>                                
                                    <vaadin-icon icon="${viewRoute.icon}" slot="prefix"></vaadin-icon>
                                    ${viewRoute.title}
                                </vaadin-side-nav-item>
                            `
                        )}
                    </vaadin-side-nav>
                </vaadin-scroller>

                <vaadin-drawer-toggle slot="navbar" aria-label="Menu toggle"></vaadin-drawer-toggle>
                <h2 slot="navbar" class="text-l m-0">${appStore.currentViewTitle}</h2>

                <slot></slot>
            </vaadin-app-layout>
        `;
    }

    connectedCallback(): void {
        super.connectedCallback();
        this.classList.add('block', 'h-full');
    }

    private getMenuRoutes(): RouteInfo[] {
        return [...(appStore.applications.filter((app) => !!app.title) as RouteInfo[])];
    }
}