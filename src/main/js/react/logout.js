import React, { useState } from 'react';
import { isCurrentUserAuthenticated } from '../rumpus'; // TODO: this is a problem. need to fix. - chuck

export default function Logout() {
    const logout = <form method="post" action="/logout"><button className="logoutBtn button is-danger" type="submit" value="Sign Out">Sign Out</button></form>;
    const is_user_authenticated = isCurrentUserAuthenticated();
    return is_user_authenticated.isAuthenticated ? logout : <></>;
}