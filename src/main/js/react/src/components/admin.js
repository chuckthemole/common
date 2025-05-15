import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import { isCurrentUserAuthenticated, getCurrentUserAuthorities } from './common_requests';
import BasePath from './base_path';

export default function Admin() {

    const base_path = BasePath();
    const [full_path_to_current_user_info, setFullPathCurrentUserInfo] = useState(''); // the current user info path
    const user_authorities = getCurrentUserAuthorities({get_user_auth_path: full_path_to_current_user_info}); // the current user info
    const is_user_authenticated = isCurrentUserAuthenticated(); // is the current user authenticated

    // Leaving for debugging purposes
    // console.log('User is authenticated: ' + is_user_authenticated.isAuthenticated);
    // console.log('User authorities: ' + user_authorities);
    // console.log('Base path: ' + base_path.BASE_PATH);
    // console.log('full path to current user info: ' + full_path_to_current_user_info);

    if(base_path.BASE_PATH_CHILD_LIST !== undefined) {
        const base_path_child_list = base_path.BASE_PATH_CHILD_LIST;
        if(base_path_child_list.common_paths !== undefined && base_path_child_list.common_paths.CurrentUserInfo !== undefined) {
            if(full_path_to_current_user_info === '') {
                setFullPathCurrentUserInfo(base_path.BASE_PATH + base_path_child_list.common_paths.CurrentUserInfo);
            }
            if(is_user_authenticated !== undefined && is_user_authenticated.isAuthenticated !== undefined) {
                if(is_user_authenticated.isAuthenticated) {
                    if(user_authorities === undefined || user_authorities.isLoading || user_authorities.isError) {
                        console.log('Error finding user authorities!');
                    } else {
                        return user_authorities.includes('ROLE_ADMIN') ? <Link to={`/admin`} className="adminBtn button is-info"><strong>Admin</strong></Link> : <></>;
                    }
                }
            }
        }
    }
    
    return <></>;
}