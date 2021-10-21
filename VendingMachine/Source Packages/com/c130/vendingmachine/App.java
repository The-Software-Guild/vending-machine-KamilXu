package com.c130.vendingmachine;

import com.c130.vendingmachine.controller.VendingMachineController;
import com.c130.vendingmachine.dao.VendingMachineAuditDao;
import com.c130.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.c130.vendingmachine.dao.VendingMachineDao;
import com.c130.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.c130.vendingmachine.service.VendingMachineServiceLayer;
import com.c130.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.c130.vendingmachine.ui.UserIO;
import com.c130.vendingmachine.ui.UserIOConsoleImpl;
import com.c130.vendingmachine.ui.VendingMachineView;

public class App {
    public static void main(String[] args) {
        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoFileImpl();
        VendingMachineDao dao = new VendingMachineDaoFileImpl();
        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(auditDao, dao);
        VendingMachineController controller = new VendingMachineController(view, service);
        controller.run();
    }
}