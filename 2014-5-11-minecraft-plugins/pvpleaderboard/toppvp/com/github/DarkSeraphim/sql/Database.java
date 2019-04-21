package com.github.DarkSeraphim.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Database
{

    protected Connection con;
    private final Logger log;

    protected Database(final Logger log)
    {
        this.log = log;
    }

    public abstract boolean initialize();

    public abstract boolean connect();

    public void close()
    {
        try
        {
            this.con.close();
            this.con = null;
        }
        catch (final SQLException ex)
        {
            this.log("An exception occurred while closing the connection: %s", ex.getMessage());
        }
    }

    public boolean isReady()
    {
        if (this.con == null)
        {
            this.log("Tried to execute a query or to prepare a statement while the connection was not ready.");
            return false;
        }
        return true;
    }

    public abstract boolean checkTable(String name);

    public abstract void createTable(TableBuilder builder);

    public abstract ResultSet executeQuery(String query);

    public abstract PreparedStatement prepare(String query);

    public static void synchronizedExecuteUpdate(final PreparedStatement stmt, final Object lock, final Object... params)
    {
        synchronized (lock)
        {
            try
            {
                for (int i = 1; i <= params.length; i++)
                {
                    stmt.setObject(i, params[i - 1]);
                }
                stmt.executeUpdate();
            }
            catch (final SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Executes the query synchronized and inserts the values in the given Map
     *
     * @param results The Map<String, Object> of which the keys represent the
     * requested columns
     * @param stmt The PreparedStatement to execute
     * @param lock The lock for the synchronization
     * @param params The parameters for the PreparedStatement
     * @return true when all columns found as Map keys are found and inserted
     * into the map.
     */
    public static boolean synchronizedExecuteQuery(Map<String, Object> results, final PreparedStatement stmt, final Object lock, final Object... params)
    {
        final List<Map<String, Object>> resultsList = new ArrayList<Map<String, Object>>();
        resultsList.add(results);
        
        if(!Database.synchronizedExecuteQuery(resultsList, stmt, lock, params))
            return false;
        for(Map.Entry<String, Object> entry : resultsList.get(0).entrySet())
        {
            results.put(entry.getKey(), entry.getValue());
        }
        return true;
    }
    
    /**
     * Executes the query synchronized and inserts the values in the given Map
     *
     * @param results The Map<String, Object> of which the keys represent the
     * requested columns
     * @param stmt The PreparedStatement to execute
     * @param lock The lock for the synchronization
     * @param params The parameters for the PreparedStatement
     * @return true when all columns found as Map keys are found and inserted
     * into the map.
     */
    public static boolean synchronizedExecuteQuery(final List<Map<String, Object>> results, final PreparedStatement stmt, final Object lock, final Object... params)
    {
        if(results.size() <= 0) return false;
        
        Map<String, Object> template = new HashMap<String,Object>();
        for(String key : results.get(0).keySet())
        {
            template.put(key, null);
        }
        
        results.clear();
        
        ResultSet rs = null;

        boolean success = false;

        synchronized (lock)
        {
            try
            {
                for (int i = 1; i <= params.length; i++)
                {
                    stmt.setObject(i, params[i - 1]);
                }
                rs = stmt.executeQuery();
            }
            catch (final SQLException ex)
            {
                ex.printStackTrace();
            }

            if (rs == null)
            {
                return false;
            }

            try
            {
                while(rs.next())
                {
                    Map<String, Object> result = new HashMap<String, Object>();
                    for (final String column : template.keySet())
                    {
                        result.put(column, rs.getObject(column));
                    }
                    results.add(result);
                }
                success = results.size() > 0;
            }
            catch (final SQLException ex)
            {
                // Swallow the exception
            }
            finally
            {
                try
                {
                    rs.close();
                }
                catch (final SQLException ex)
                {
                    // Swallow the exception
                }
            }
            try
            {
                if (!stmt.getConnection().getAutoCommit())
                {
                    stmt.getConnection().commit();
                }
            }
            catch (final SQLException ex)
            {
                // Swallow the exception
            }
        }
        return success;
    }

    protected void log(final String msg, final Object... o)
    {
        this.log.log(Level.SEVERE, String.format(msg, o));
    }
}
