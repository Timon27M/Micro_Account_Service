CREATE TABLE IF NOT EXISTS account_profile (
    account_id UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE,
    account_number VARCHAR(34) NOT NULL UNIQUE,

    account_type VARCHAR(20) NOT NULL DEFAULT 'CURRENT',
    currency CHAR(3) NOT NULL DEFAULT 'RUB',

    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',

    available_balance NUMERIC(19,4) DEFAULT 0,
    current_balance NUMERIC(19,4) DEFAULT 0,

    opened_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    closed_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,

    CONSTRAINT pk_account_profile PRIMARY KEY (account_id),

    CONSTRAINT check_status CHECK (status IN ('PENDING', 'ACTIVE', 'BLOCKED', 'SUSPENDED', 'CLOSED')),
    CONSTRAINT check_account_type CHECK (account_type IN ('CURRENT', 'SAVINGS', 'DEPOSIT', 'CREDIT')),
    CONSTRAINT check_currency CHECK (currency IN ('USD', 'EUR', 'RUB'))
);

CREATE INDEX idx_account_number ON account_profile (account_number);