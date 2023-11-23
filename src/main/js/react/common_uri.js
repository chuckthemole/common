// import React, { useState } from 'react';

import useSWR from 'swr';

const fetcher = (...args) => fetch(...args).then((res) => res.json());

export function getCommonPaths() {
    const { data, error, isLoading } = useSWR(
        "/common/api/paths",
        fetcher
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
        fetcher
    );
    return {
        common_paths: data,
        isLoading,
        isError: error
    }

}

export function isCurrentUserAuthenticatedCommon() {
    const { data, error, isLoading } = useSWR(
        "/common/api/is_authenticated",
        fetcher, {
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

export function currentUserInfo({get_user_info_path}) {
    const { data, error, isLoading } = useSWR(
        get_user_info_path,
        fetcher
    );
    return {
        user_info: data,
        isLoading,
        isError: error
    }
}