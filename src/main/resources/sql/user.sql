CREATE TABLE `users`
(
    `id`                      BIGINT(19)               NOT NULL AUTO_INCREMENT,
    `name`                    VARCHAR(255)             NULL DEFAULT NULL,
    `email`                   VARCHAR(255)             NULL DEFAULT NULL,
    `password`                VARCHAR(255)             NULL DEFAULT NULL,
    `algorithm`               ENUM ('BCRYPT','SCRYPT') NULL DEFAULT NULL,
    `enabled`                 BIT(1)                   NOT NULL,
    `credentials_non_expired` BIT(1)                   NOT NULL,
    `login_count`             INT(10)                  NOT NULL,
    `non_blocked`             BIT(1)                   NOT NULL,
    `non_expired`             BIT(1)                   NOT NULL,
    `create_at`               DATETIME(6)              NULL DEFAULT NULL,
    `last_login_at`           DATETIME(6)              NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
;


