import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import { getPathsFromBasePath, isCurrentUserAuthenticated, currentUserInfo, getCurrentBasePath, getCurrentUserAuthorities } from './common_requests';

export default function Admin() {

    // TODO: this all seems like common code that should be in a common place. check out common_requests.js - chuck
    const current_base_path = getCurrentBasePath(); // the current base path
    const [base_path, setBasePath] = useState('/'); // the current base path as a state, default is '/'
    const base_path_child_list = getPathsFromBasePath(base_path); // this list of paths accociated with the current base path
    const [full_path_to_current_user_info, setFullPathCurrentUserInfo] = useState(''); // the current user info path
    const user_authorities = getCurrentUserAuthorities({get_user_auth_path: full_path_to_current_user_info}); // the current user info
    const is_user_authenticated = isCurrentUserAuthenticated(); // is the current user authenticated

    useEffect(() => { // set the base path when the current_base_path hook changes
        if(current_base_path.current_base_path !== undefined && current_base_path.current_base_path.path !== undefined) {
            setBasePath(current_base_path.current_base_path.path);
        }
    }, [current_base_path]);

    if(base_path_child_list !== undefined && base_path_child_list.common_paths !== undefined && base_path_child_list.common_paths.CurrentUserInfo !== undefined) {
        if(full_path_to_current_user_info === '') {
            setFullPathCurrentUserInfo(base_path + base_path_child_list.common_paths.CurrentUserInfo);
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
    
    return <></>;
}