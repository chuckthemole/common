const React = require('react');
import useSWR from 'swr';
import { Tooltip as ReactTooltip } from "react-tooltip";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faExclamationTriangle, faUser } from '@fortawesome/free-solid-svg-icons';

const fetcher = (...args) => fetch(...args).then((res) => res.json());

export default function UserIcon({current_user_path}) {
    const { data, error } = useSWR(
        current_user_path,
        fetcher
    );

    if (error) return (
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
    
    // if (!data) return <p>Loading</p>;

    if (!data) return(
        <div className='container m-6'>
            <progress className="progress is-small is-primary" max="100">15%</progress>
        </div>
    )
   
    return (
        <>
            <a
                data-tooltip-id="my-tooltip"
                data-tooltip-html={
                    "Username: " + data.username +
                    "<br />Email: " + data.email
                }
                data-tooltip-place="bottom"
            >
                ◕‿‿◕
            </a>
            <ReactTooltip id="my-tooltip" />
        </>
    )
}