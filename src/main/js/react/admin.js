import React, { useState } from 'react';
import { Link } from 'react-router-dom';

import { isCurrentUserAuthenticated, getCurrentUserAuthorities } from '../rumpus'; // TODO: this is a problem. need to fix. - chuck

export default function Admin() {

    const [is_user_authenticated, setIsUserAuthenticated] = useState(isCurrentUserAuthenticated());

    if(is_user_authenticated.isAuthenticated) {
        const user_authorities = getCurrentUserAuthorities();
        return user_authorities.includes('ROLE_ADMIN') ? <Link to={`/admin`} className="adminBtn button is-info"><strong>Admin</strong></Link> : <></>;
    } else {
        return <></>;
    }
}