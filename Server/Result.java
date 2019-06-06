import java.sql.ResultSet;
import java.sql.Statement;

public class Result {
    private ResultSet resultSet;
    private Statement statement;
    public Result(ResultSet resultSet,Statement statement){
        this.resultSet=resultSet;
        this.statement=statement;
    }
    public ResultSet getResultSet(){
        return resultSet;
    }
}
