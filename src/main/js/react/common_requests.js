// import React, { useState } from 'react';

// TODO: why is this in react dir? - chuck

import useSWR from 'swr';

const fetcher = (...args) => fetch(...args).then((res) => res.json());

export const common_fetcher = async url => {
    const res = await fetch(url)
    // If the status code is not in the range 200-299,
    // still try to parse and throw it.
    if (!res.ok) {
      const error = new Error('An error occurred while fetching the data.')
      // Attach extra info to the error object.
      error.info = await res.json()
      error.status = res.status
      throw error
    }
    return res.json()
}

/**
 * Common loader for all common requests
 * 
 * @param {string} path path to GET request
 * @returns {object} {data, error, isLoading}
 */
export function common_loader(path) {
    const { data, error, isLoading } = useSWR(
        path,
        common_fetcher
    );
    return {
        data: data,
        error: error,
        isLoading: isLoading
    }
}

export function getCurrentBasePath() {
    const { data, error, isLoading } = useSWR(
        "/common/api/current_base_path",
        common_fetcher
    );

    return {
        current_base_path: data,
        isLoading,
        isError: error
    }

}

export function getCommonPaths() {
    const { data, error, isLoading } = useSWR(
        "/common/api/paths",
        common_fetcher
    );

    return {
        common_paths: data,
        isLoading,
        isError: error
    }
}

export function getPathsFromBasePath(base_path) {
    const { data, error, isLoading } = useSWR(
        "common/api/paths" + base_path,
        common_fetcher
    );
    return {
        common_paths: data,
        isLoading,
        isError: error
    }

}

export function isCurrentUserAuthenticated() {
    const { data, error, isLoading } = useSWR(
        "/common/api/is_authenticated",
        common_fetcher, {
            // revalidateOnFocus: false,
            // revalidateOnReconnect: false,
            // refreshWhenOffline: false,
            // refreshWhenHidden: false,
            // refreshInterval: 2000
        }
    );

    return {
        isAuthenticated: data,
        isLoading,
        isError: error
    }
}

export function getCurrentUserAuthorities({get_user_auth_path}) {
    const { data, error, isLoading } = useSWR(
        get_user_auth_path,
        common_fetcher
    );

    let authorities = [];
    if(!error && data !== undefined) {
        for(let i = 0; i < data.userDetails.authorities.length; i++) {
            authorities.push(data.userDetails.authorities[i].authority);
        }
    }

    return authorities;
}

export function currentUserInfo({get_user_info_path}) {
    const { data, error, isLoading } = useSWR(
        get_user_info_path,
        common_fetcher
    );
    return {
        user_info: data,
        isLoading,
        isError: error
    }
}