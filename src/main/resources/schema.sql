create table config (
   app_code varchar(30) not null,
    version varchar(20) not null,
    config_detail clob,
    last_update TIMESTAMP AS CURRENT_TIMESTAMP,
    primary key (app_code, version)
);