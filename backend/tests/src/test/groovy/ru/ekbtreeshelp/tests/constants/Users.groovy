package ru.ekbtreeshelp.tests.constants

import ru.ekbtreeshelp.tests.data.TestUser

abstract class Users {
    public static final TestUser SUPER_USER = new TestUser(
            'superuser@ekbtreeshelp.ru',
            'ItIsChangedInProduction'
    )
}

abstract class CookieNames {
    public static final String ACCESS_TOKEN = 'AccessToken'
    public static final String REFRESH_TOKEN = 'RefreshToken'
}