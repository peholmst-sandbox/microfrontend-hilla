import {login as loginImpl, LoginResult, logout as logoutImpl} from '@hilla/frontend';
import {UserInformationEndpoint} from './generated/endpoints';

export interface UserDetails {
    username: string;
    displayName: string;
}

interface Authentication {
    timestamp: number;
    userDetails: UserDetails | undefined;
}

let authentication: Authentication | undefined = undefined;

const AUTHENTICATION_KEY = 'authentication';
const THIRTY_DAYS_MS = 30 * 24 * 60 * 60 * 1000;

const storedAuthenticationJson = localStorage.getItem(AUTHENTICATION_KEY);
if (storedAuthenticationJson !== null) {
    const storedAuthentication = JSON.parse(storedAuthenticationJson) as Authentication;
    // Check that the stored timestamp is not older than 30 days
    const hasRecentAuthenticationTimestamp = new Date().getTime() - storedAuthentication.timestamp < THIRTY_DAYS_MS;
    if (hasRecentAuthenticationTimestamp) {
        // Use loaded authentication
        authentication = storedAuthentication;
    } else {
        // Delete expired stored authentication
        setSessionExpired();
    }
}

// TODO How and when is this function to be called?
export async function login(username: string, password: string): Promise<LoginResult> {
    const result = await loginImpl(username, password);
    if (!result.error) {
        const userDetails = await UserInformationEndpoint.getCurrentUser();
        authentication = {
            timestamp: new Date().getTime(),
            userDetails: userDetails,
        };
        // Save the authentication to local storage
        localStorage.setItem(AUTHENTICATION_KEY, JSON.stringify(authentication));
    }
    return result;
}

export function setSessionExpired() {
    authentication = undefined;
    localStorage.removeItem(AUTHENTICATION_KEY);
}

export async function logout() {
    setSessionExpired();
    await logoutImpl();
    // TODO Fire event
    location.href = '';
}

export async function getUser(): Promise<UserDetails | undefined> {
    console.log(`Fetching user information from server`);
    return await UserInformationEndpoint.getCurrentUser();
}

export function isAuthenticated(): boolean {
    return !!authentication;
}

export function hasAuthority(authority: string): boolean {
    return false; // TODO Implement me
}
