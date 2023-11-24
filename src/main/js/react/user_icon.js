import React, { useEffect, useState } from 'react';
import { Tooltip as ReactTooltip } from "react-tooltip";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faExclamationTriangle, faUser } from '@fortawesome/free-solid-svg-icons';
import { getPathsFromBasePath, isCurrentUserAuthenticated, currentUserInfo, getCurrentBasePath } from './common_uri';

export default function UserIcon() {
    const current_base_path = getCurrentBasePath(); // the current base path
    const [base_path, setBasePath] = useState('/'); // the current base path as a state, default is '/'
    const base_path_child_list = getPathsFromBasePath(base_path); // this list of paths accociated with the current base path
    const [base_path_current_user_info, setBasePathCurrentUserInfo] = useState(''); // the current user info path
    const user_info = currentUserInfo({get_user_info_path: base_path_current_user_info}); // the current user info
    const is_user_authenticated = isCurrentUserAuthenticated(); // is the current user authenticated

    useEffect(() => { // set the base path when the current_base_path hook changes
        if(current_base_path.current_base_path !== undefined && current_base_path.current_base_path.path !== undefined) {
            setBasePath(current_base_path.current_base_path.path);
        }
    }, [current_base_path]);

    if(!is_user_authenticated.isAuthenticated) { // no icon to display if user is not authenticated
        return (<></>);
    } else if(base_path_child_list === undefined || base_path_child_list.common_paths === undefined || base_path_child_list.common_paths.CurrentUserInfo === undefined) {
        console.log('Error finding user icon!');
        return(
            <>
                <a
                    data-tooltip-id="my-tooltip"
                    data-tooltip-html={
                        "Error finding user!"
                    }
                    data-tooltip-place="bottom"
                >
                    <FontAwesomeIcon icon={faExclamationTriangle} />
                </a>
                <ReactTooltip id="my-tooltip" />
            </>
        );
    } else {

        if(base_path_current_user_info === '') {
            // console.log("Setting base_path_current_user_info: " + base_path + base_path_child_list.common_paths.CurrentUserInfo);
            setBasePathCurrentUserInfo(base_path + base_path_child_list.common_paths.CurrentUserInfo);
        }

        if (user_info.isError) {
            return (
                <>
                    <a
                        data-tooltip-id="my-tooltip"
                        data-tooltip-html={
                            "Error finding user!"
                        }
                        data-tooltip-place="bottom"
                    >
                        <FontAwesomeIcon icon={faExclamationTriangle} />
                    </a>
                    <ReactTooltip id="my-tooltip" />
                </>
            )
        }

        if(user_info.isLoading) {
            return (
                <>
                    <a
                        data-tooltip-id="my-tooltip"
                        data-tooltip-html={
                            "Loading..."
                        }
                        data-tooltip-place="bottom"
                    >
                        <FontAwesomeIcon icon={faUser} />
                    </a>
                    <ReactTooltip id="my-tooltip" />
                </>
            );
        }

        if(!user_info.user_info) {
            return(
                <div className='container m-6'>
                    <progress className="progress is-small is-primary" max="100">15%</progress>
                </div>
            )
        }

        if(user_info.user_info === undefined && user_info.isError === undefined) {
            console.log('Error finding user icon... user_info and error is undefined!');
            return(
                <>
                    <a
                        data-tooltip-id="my-tooltip"
                        data-tooltip-html={
                            "Error finding user!"
                        }
                        data-tooltip-place="bottom"
                    >
                        <FontAwesomeIcon icon={faExclamationTriangle} />
                    </a>
                    <ReactTooltip id="my-tooltip" />
                </>
            );
        }
        
        return (
            <>
                <div className="navbar-item">
                    <a
                        data-tooltip-id="my-tooltip"
                        data-tooltip-html={
                            "Username: " + user_info.user_info.username +
                            "<br />Email: " + user_info.user_info.email
                        }
                        data-tooltip-place="bottom"
                    >
                        ◕‿‿◕
                    </a>
                    <ReactTooltip id="my-tooltip" />
                </div>
            </>
        );
    }
}