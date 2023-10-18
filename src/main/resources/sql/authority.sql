# CREATE TABLE `authority`
# (
#     `id`   INT(10)      NOT NULL AUTO_INCREMENT,
#     `user` BIGINT(19)   NULL DEFAULT NULL,
#     `name` VARCHAR(255) NULL DEFAULT NULL,
#     PRIMARY KEY (`id`) USING BTREE,
#     FOREIGN KEY (`user`) REFERENCES `user` (`id`)
# )
# ;