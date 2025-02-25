package dao;

import exceptions.DAOException;

import java.sql.SQLException;

public interface GenericProcedureDAO <P, R>{
        R execute (P param) throws SQLException, DAOException;
}
