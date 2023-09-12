import {appStore} from './stores/app-store';
import {ApplicationsEndpoint} from './generated/endpoints';

// TODO Add event listener to update the applications when new apps are registered and old ones disappear
appStore.setApplications(await ApplicationsEndpoint.getApplications());
