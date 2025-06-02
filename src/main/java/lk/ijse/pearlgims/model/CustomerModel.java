package lk.ijse.pearlgims.model;

import lk.ijse.pearlgims.dto.CustomerDTO;
import lk.ijse.pearlgims.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public String getNextCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber+1;

            String nextIdString = String.format("C%03d",nextIdNumber);
            return nextIdString;
        }
        return "C001";
    }

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                customerDTO.getCustomerID(),
                customerDTO.getName(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getAddress()
        );
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDTO> customerDTOArrayList = new ArrayList<>();
        while (resultSet.next()){
            CustomerDTO customerDTO = new CustomerDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
            customerDTOArrayList.add(customerDTO);
        }

        return customerDTOArrayList;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update customer set name=?, contact=?, email=?, address=? where customer_id=?",
                    customerDTO.getName(),
                    customerDTO.getContact(),
                    customerDTO.getEmail(),
                    customerDTO.getAddress(),
                    customerDTO.getCustomerID()
                );
    }

    public boolean deleteCustomer(String customerID) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from customer where customer_id=?",customerID);
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    public String findNameById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute(
                "select name from customer where customer_id=?",
                selectedCustomerId
        );

        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
