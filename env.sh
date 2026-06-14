#!/bin/bash

set -e

export LOGIN_PASSWORD=""
export LOGIN_EMAIL=""
export LOGIN_NEW_PASSWORD=""

export LOGIN="https://opencart.liveblog365.com/index.php?route=account/login&language=en-gb"
export FORGOTTEN="https://opencart.liveblog365.com/index.php?route=account/forgotten&language=en-gb"
export REGISTER="https://opencart.liveblog365.com/index.php?route=account/register&language=en-gb"

export ACCOUNT="https://opencart.liveblog365.com/index.php?route=account/account&language=en-gb"
export EDIT_ACCOUNT="https://opencart.liveblog365.com/index.php?route=account/edit&language=en-gb"
export CHANGE_PASSWORD="https://opencart.liveblog365.com/index.php?route=account/password&language=en-gb"
export LOGOUT="https://opencart.liveblog365.com/index.php?route=account/logout&language=en-gb"

export HOME_PAGE="https://opencart.liveblog365.com/index.php?route=common/home&language=en-gb"
export CHECKOUT="https://opencart.liveblog365.com/index.php?route=checkout/checkout&language=en-gb"


#allure serve target/allure-results
