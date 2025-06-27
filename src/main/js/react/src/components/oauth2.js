import React from 'react';
import { Github, Mail, Linkedin, User, MessageCircle, Twitter, Apple, GitBranch, Slack } from 'lucide-react';

// OAuth2 Provider Configuration matching your backend enum
const OAUTH_PROVIDERS = {
    google: {
        name: 'Google',
        icon: Mail,
        colors: {
            bg: 'bg-red-500 hover:bg-red-600',
            text: 'text-white',
            border: 'border-red-500'
        },
        endpoint: '/oauth2/authorization/google'
    },
    github: {
        name: 'GitHub',
        icon: Github,
        colors: {
            bg: 'bg-gray-800 hover:bg-gray-900',
            text: 'text-white',
            border: 'border-gray-800'
        },
        endpoint: '/oauth2/authorization/github'
    },
    microsoft: {
        name: 'Microsoft',
        icon: User,
        colors: {
            bg: 'bg-blue-500 hover:bg-blue-600',
            text: 'text-white',
            border: 'border-blue-500'
        },
        endpoint: '/oauth2/authorization/microsoft'
    },
    facebook: {
        name: 'Facebook',
        icon: MessageCircle,
        colors: {
            bg: 'bg-blue-600 hover:bg-blue-700',
            text: 'text-white',
            border: 'border-blue-600'
        },
        endpoint: '/oauth2/authorization/facebook'
    },
    discord: {
        name: 'Discord',
        icon: MessageCircle,
        colors: {
            bg: 'bg-indigo-500 hover:bg-indigo-600',
            text: 'text-white',
            border: 'border-indigo-500'
        },
        endpoint: '/oauth2/authorization/discord'
    },
    twitter: {
        name: 'Twitter',
        icon: Twitter,
        colors: {
            bg: 'bg-sky-500 hover:bg-sky-600',
            text: 'text-white',
            border: 'border-sky-500'
        },
        endpoint: '/oauth2/authorization/twitter'
    },
    linkedin: {
        name: 'LinkedIn',
        icon: Linkedin,
        colors: {
            bg: 'bg-blue-700 hover:bg-blue-800',
            text: 'text-white',
            border: 'border-blue-700'
        },
        endpoint: '/oauth2/authorization/linkedin'
    },
    apple: {
        name: 'Apple',
        icon: Apple,
        colors: {
            bg: 'bg-black hover:bg-gray-800',
            text: 'text-white',
            border: 'border-black'
        },
        endpoint: '/oauth2/authorization/apple'
    },
    gitlab: {
        name: 'GitLab',
        icon: GitBranch,
        colors: {
            bg: 'bg-orange-500 hover:bg-orange-600',
            text: 'text-white',
            border: 'border-orange-500'
        },
        endpoint: '/oauth2/authorization/gitlab'
    },
    slack: {
        name: 'Slack',
        icon: Slack,
        colors: {
            bg: 'bg-purple-500 hover:bg-purple-600',
            text: 'text-white',
            border: 'border-purple-500'
        },
        endpoint: '/oauth2/authorization/slack'
    }
};

// OAuth2 Button Component
const OAuth2Button = ({
    provider,
    action = 'sign-in',
    variant = 'filled',
    size = 'medium',
    fullWidth = false,
    baseUrl = 'http://localhost:8888',
    onAuthStart,
    onAuthError,
    className = '',
    children
}) => {
    const providerConfig = OAUTH_PROVIDERS[provider.toLowerCase()];

    if (!providerConfig) {
        console.warn(`OAuth2Button: Unknown provider "${provider}"`);
        return null;
    }

    const { name, icon: Icon, colors, endpoint } = providerConfig;

    // Size classes
    const sizeClasses = {
        small: 'px-3 py-2 text-sm',
        medium: 'px-4 py-2.5 text-base',
        large: 'px-6 py-3 text-lg'
    };

    // Variant classes
    const getVariantClasses = () => {
        switch (variant) {
            case 'outlined':
                return `bg-white hover:bg-gray-50 text-gray-700 border-2 ${colors.border}`;
            case 'ghost':
                return `bg-transparent hover:bg-gray-100 text-gray-700 border border-gray-300`;
            default: // filled
                return `${colors.bg} ${colors.text} border border-transparent`;
        }
    };

    const handleAuth = async () => {
        try {
            onAuthStart?.();

            // Store the current page for redirect after auth
            sessionStorage.setItem('oauth_redirect_url', window.location.pathname);

            // Redirect to your Spring Boot OAuth2 endpoint
            window.location.href = `${baseUrl}${endpoint}`;

        } catch (error) {
            console.error('OAuth2 authentication error:', error);
            onAuthError?.(error);
        }
    };

    const actionText = action === 'sign-up' ? 'Sign up' : 'Sign in';

    return (
        <button
            onClick={handleAuth}
            className={`
        inline-flex items-center justify-center gap-2 
        font-medium rounded-lg transition-colors duration-200
        focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500
        disabled:opacity-50 disabled:cursor-not-allowed
        ${sizeClasses[size]}
        ${getVariantClasses()}
        ${fullWidth ? 'w-full' : ''}
        ${className}
      `}
        >
            <Icon size={size === 'small' ? 16 : size === 'large' ? 24 : 20} />
            {children || `${actionText} with ${name}`}
        </button>
    );
};

// OAuth2 Provider Group Component
const OAuth2ButtonGroup = ({
    providers = ['google', 'github'],
    action = 'sign-in',
    variant = 'filled',
    size = 'medium',
    fullWidth = true,
    baseUrl = 'http://localhost:8888',
    onAuthStart,
    onAuthError,
    className = ''
}) => {
    return (
        <div className={`space-y-3 ${className}`}>
            {providers.map((provider) => (
                <OAuth2Button
                    key={provider}
                    provider={provider}
                    action={action}
                    variant={variant}
                    size={size}
                    fullWidth={fullWidth}
                    baseUrl={baseUrl}
                    onAuthStart={onAuthStart}
                    onAuthError={onAuthError}
                />
            ))}
        </div>
    );
};

// Demo Component
const OAuth2Demo = () => {
    const handleAuthStart = () => {
        console.log('Authentication started...');
    };

    const handleAuthError = (error) => {
        console.error('Authentication failed:', error);
        alert('Authentication failed. Please try again.');
    };

    return (
        <div className="max-w-md mx-auto p-6 bg-white rounded-lg shadow-lg space-y-8">
            <div className="text-center">
                <h2 className="text-2xl font-bold text-gray-900 mb-2">Welcome Back</h2>
                <p className="text-gray-600">Sign in to your account</p>
            </div>

            {/* Single Button Examples */}
            <div className="space-y-4">
                <h3 className="text-lg font-semibold text-gray-800">Single Buttons</h3>

                <OAuth2Button
                    provider="google"
                    action="sign-in"
                    fullWidth
                    onAuthStart={handleAuthStart}
                    onAuthError={handleAuthError}
                />

                <OAuth2Button
                    provider="microsoft"
                    action="sign-up"
                    variant="outlined"
                    fullWidth
                    onAuthStart={handleAuthStart}
                    onAuthError={handleAuthError}
                />

                <OAuth2Button
                    provider="discord"
                    variant="ghost"
                    size="small"
                    onAuthStart={handleAuthStart}
                    onAuthError={handleAuthError}
                />
            </div>

            {/* Button Group Example */}
            <div className="space-y-4">
                <h3 className="text-lg font-semibold text-gray-800">Button Group</h3>

                <OAuth2ButtonGroup
                    providers={['google', 'github', 'microsoft', 'discord']}
                    action="sign-in"
                    onAuthStart={handleAuthStart}
                    onAuthError={handleAuthError}
                />
            </div>

            {/* Custom Styled Example */}
            <div className="space-y-4">
                <h3 className="text-lg font-semibold text-gray-800">Custom Style</h3>

                <OAuth2Button
                    provider="google"
                    variant="outlined"
                    size="large"
                    fullWidth
                    className="shadow-md hover:shadow-lg"
                    onAuthStart={handleAuthStart}
                    onAuthError={handleAuthError}
                >
                    Continue with Google
                </OAuth2Button>
            </div>
        </div>
    );
};

export default OAuth2Demo;