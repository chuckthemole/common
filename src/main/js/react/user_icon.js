import React, { useState } from 'react';
import useSWR from 'swr';
import { Tooltip as ReactTooltip } from "react-tooltip";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faExclamationTriangle, faUser } from '@fortawesome/free-solid-svg-icons';
import { isCurrentUserAuthenticated } from '../rumpus'; // TODO: this is a problem. need to fix. check out CommonRestController.java - chuck
import { getCommonPaths, getPathsFromBasePath, isCurrentUserAuthenticatedCommon, currentUserInfo } from './common_uri';

const fetcher = (...args) => fetch(...args).then((res) => res.json());

export default function UserIcon({current_user_path}) {
    const base_path = '/api';
    const paths_rumpus = getPathsFromBasePath(base_path);
    const [current_user_info_path, setCurrentUserInfoPath] = useState('');
    const user_info = currentUserInfo({get_user_info_path: current_user_info_path});
    const is_user_authenticated = isCurrentUserAuthenticated();

    if(!is_user_authenticated.isAuthenticated) { // no icon to display if user is not authenticated
        return (<></>);
    } else if(paths_rumpus === undefined || paths_rumpus.common_paths === undefined || paths_rumpus.common_paths.CurrentUserInfo === undefined) {
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

        if(current_user_info_path === '') {
            // console.log("Setting current_user_info_path: " + base_path + paths_rumpus.common_paths.CurrentUserInfo);
            setCurrentUserInfoPath(base_path + paths_rumpus.common_paths.CurrentUserInfo);
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