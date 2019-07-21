# 查询根节点下的所有子节点id集合
drop function if exists selectOrganizationChildren;
DELIMITER //
CREATE FUNCTION `selectOrganizationChildren`(rootId varchar(50), containRoot char(1))
    RETURNS mediumtext
    READS SQL DATA

BEGIN
    DECLARE sTemp mediumtext;

    DECLARE sTempChd mediumtext;

    SET sTemp = '';

    SET sTempChd = rootId;

    WHILE sTempChd is not null DO
    if sTemp != '' then
        SET sTemp = concat(sTemp, ',', sTempChd);
    else
        SET sTemp = concat(sTemp, sTempChd);
    end if ;

    SELECT group_concat(id) INTO sTempChd FROM organization where FIND_IN_SET(parent_id, sTempChd) > 0;
    END WHILE;

    if '1' != containRoot then
        set sTemp = substring(sTemp, length(rootId) + 2);
    end if;
    RETURN sTemp;

END //
DELIMITER ;