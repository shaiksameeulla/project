DROP FUNCTION IF EXISTS getNthWorkingDay;
CREATE FUNCTION `getNthWorkingDay`(
  currentdate  datetime
 ,extradays    int(11)
 ,branch_id    int(11)
 ,city_id      int(11)
 ,state_id     int(11)
 ,region_id    int(11)) RETURNS datetime
BEGIN
    DECLARE DaysToAdd   int(11);
    DECLARE Date_To     DATETIME;
    DECLARE Today       DATETIME;
    

    IF currentdate IS NULL
    THEN
      RETURN NULL;
    END IF;

    SET DaysToAdd = extradays;
    -- SET Date_To   = currentdate;
    -- SET Today     = Date_To;
    SET Today     = currentdate;

    WHILE DaysToAdd > 0
    DO
      IF DAYNAME( Today) IN ('Sunday') OR
         EXISTS
           (SELECT
              *
            FROM
              ff_d_holiday fdh
              JOIN ff_d_holiday_name fdhn
                ON fdhn.HOLIDAY_NAME_ID = fdh.HOLIDAY_NAME_ID
            WHERE
              (fdh.REGION_ID = region_id OR
              fdh.STATE_ID = state_id OR
              fdh.CITY_ID = city_id OR
              fdh.BRANCH_ID = branch_id) AND
              DATEDIFF(
                fdh.HOLIDAY_DATE
               ,Today) = 0)
      THEN
        SET Today      = DATE_ADD(
                           Today
                          ,INTERVAL 1 DAY);
      ELSE
          SET DaysToAdd = DaysToAdd - 1;
        -- SET Date_To   = Today;
      END IF;
    END WHILE;


    RETURN Today;
  END;
