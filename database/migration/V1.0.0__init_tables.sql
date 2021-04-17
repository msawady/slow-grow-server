CREATE TABLE IF NOT EXISTS Asset
(
    id          varchar(32) primary key,
    symbol      varchar(32),
    asset_class varchar(32)
);

CREATE TABLE IF NOT EXISTS Investor
(
    id    varchar(32) primary key,
    email varchar(1024) not null
);

CREATE TABLE IF NOT EXISTS Portfolio
(
    id          varchar(32) primary key,
    investor_id varchar(32)   not null REFERENCES Investor (id),
    name        varchar(1024) not null
);

CREATE TABLE IF NOT EXISTS Position
(
    id           varchar(32) primary key,
    investor_id  varchar(32) not null REFERENCES Investor (id),
    portfolio_id varchar(32) not null REFERENCES Portfolio (id),
    asset_id     varchar(32) not null REFERENCES Asset (id),
    side         VARCHAR(32) not null,
    initial_qty  int         not null,
    open_date    date        not null,
    close_date   date,
    current_qty  int         not null
);
