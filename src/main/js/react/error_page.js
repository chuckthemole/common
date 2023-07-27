import * as React from 'react';
import { useRouteError } from "react-router-dom";

export default function ErrorPage() {
    const error = useRouteError();
    console.error(error);

    return (
        <div id='error_page' className="is-flex is-justify-content-center is-align-items-center">
            <div className="has-text-centered">
                <h1 className="is-size-1 has-text-weight-bold has-text-primary">Error finding user</h1>
                <p className="is-size-5 has-text-weight-medium"> <span className="has-text-danger">Opps!</span> Page not found.</p>
                <p className="is-size-6 mb-2">
                    The page you’re looking for doesn’t exist.
                </p>
                {/* <p>
                    <i>{error.statusText || error.message}</i>
                </p> */}
                <a href="/" className="button is-primary">Go Home</a>
            </div>
        </div>
    );
}