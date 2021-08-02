import java.sql.*;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class NextPlan {
    private static final Logger LOG = Logger.getLogger(NextPlan.class.getName());
    private final Connection connection;
    private String sql;

    public NextPlan(Connection connection) {
        this.connection = connection;
        sql = getSql();
    }

    public void setLOG(Handler logHandler) {
        LOG.addHandler(logHandler);
    }

    public boolean doStatement() {
        int i = 0;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while (resultSet.next()) {
                LOG.info(resultSet.getString(i));
                i++;
            }
            return true;
        } catch (SQLException throwables) {
            LOG.warning(throwables.getMessage());
            return false;
        }
        return false;
    }

    private String getSql() {
        return """
                    execute block
                    returns
                            (
                                    info varchar(1024)
                    )
                    as
                    begin
                    select g.name from guests where g.id = 1003;
                    suspend;
                    end
                """;
    }

//    CONNECT '127.0.0.1/3080:D:\Abonement\IBDATA\FITNESS.FDB' USER 'ucs' PASSWORD 'ucs';
//    set term ^ ;
//
//    execute block
//    returns
//            (
//                    info varchar(1024)
//)
//    as
//    declare variable pp_id integer;
//    declare variable PLANNING_PERIOD_next integer;
//    declare variable success integer;
//    declare variable isGetPP integer;
//
//
//
//    begin
//    select formatdatetime('c',d2dbl('now')) from dummy into :INFO;
//    INFO = 'Start: ' || INFO;
//    suspend;
//
//    isGetPP =0;
//    select first (1) PP.ID,iif(pp.dateto -d2dbl('now') < 1,1,0) isGetPP from PLANNING_PERIODS PP where PP.ISOPEN = 1 into :pp_id,:isGetPP;
//
//    INFO = ':isGetPP='||:isGetPP;
//    suspend;
//    INFO = ':pp_id='||:pp_id;
//    suspend;
//
//    select first (1) PP.ID  from PLANNING_PERIODS PP
//    where PP.ISOPEN = 0 and
//    PP.OPENINGDATE = 0 and
//    PP.enabled = 1 and
//    PP.DATETO > D2DBL('now')
//    order by PP.DATEFROM, PP.ID
//    into :PLANNING_PERIOD_next;
//
//    INFO = ':PLANNING_PERIOD_next='||:PLANNING_PERIOD_next;
//    suspend;
//
//    success = 0;
//
//  if (:pp_id > 0 and :PLANNING_PERIOD_next > 0 and :isGetPP = 1) then
//            begin
//    select sp.success from SP_CLOSE_PLANNING_PERIOD (:pp_id,'') sp into :success;
//    INFO = 'isClose: success ='||:success;
//    suspend;
//
//    if (:success = 1) then
//            begin
//    update PLANNING_PERIODS PP set pp.isopen = 1 where pp.id = :PLANNING_PERIOD_next;
//    INFO = 'isOpen:';
//    suspend;
//    end
//            end
//    select formatdatetime('c',d2dbl('now')) from dummy into :INFO;
//    INFO = 'Stop: ' || INFO;
//    suspend;
//    end
//
//^
//    commit ^
//    set term ; ^
//
//


}
