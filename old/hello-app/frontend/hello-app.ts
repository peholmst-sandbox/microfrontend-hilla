import {Subscription} from '@hilla/frontend';
import {html, LitElement} from 'lit';
import {customElement, state} from 'lit/decorators.js';
import {ServerTimeEndpoint} from './generated/endpoints';
//@ts-ignore
import {getUser} from 'portal/auth';

//@ts-ignore
import('components/all.js');

@customElement('hello-app')
export class HelloApp extends LitElement {

    @state()
    serverTime: string = '<fetching>';

    @state()
    serverUser: string = '<fetching>';

    @state()
    clientUser: string = '<fetching>';

    @state()
    subscribedServerTime: string = '';

    @state()
    sub?: Subscription<String>;

    async connectedCallback(): Promise<void> {
        super.connectedCallback();
        this.serverTime = await ServerTimeEndpoint.getServerTime();
        this.serverUser = await ServerTimeEndpoint.getServerUser();
        this.clientUser = await getUser()?.displayName;
    }

    render() {
        return html`
            <h1>Hello World (with time)!</h1>
            <div>Server time when opening view: ${this.serverTime}</div>
            <div>Server user when opening view: ${this.serverUser}</div>
            <div>Client user when opening view: ${this.clientUser}</div>
            <div>
                ${this.sub
                        ? html`
                        <vaadin-button @click=${this.unsubscribe}>Unsubscribe</vaadin-button>
                        <span>Server time is: ${this.subscribedServerTime}</span>
                    `
                        : html`
                        <vaadin-button @click=${this.subscribe}>Subscribe to time updates</vaadin-button>
                    `}
            </div>
        `;
    }

    subscribe() {
        this.sub = ServerTimeEndpoint.subscribe().onNext((time) => (this.subscribedServerTime = time));
    }

    unsubscribe() {
        if (this.sub) {
            this.sub.cancel();
            this.sub = undefined;
        }
    }
}