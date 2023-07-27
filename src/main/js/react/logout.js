import * as React from 'react';

const logout = <form method="post" action="/logout"><button className="logoutBtn button is-danger" type="submit" value="Sign Out">Sign Out</button></form>;

export default function Logout() {
    return logout;
}