import BaseService from "./BaseService";

class AdminService extends BaseService {
    static myInstance = null;
    static getInstance() {
        if (AdminService.myInstance === null) {
            AdminService.myInstance = new AdminService();
        }
        return AdminService.myInstance
    }

    getBartenderRequests = () => this.findAllItems(`/users/bartenders`);

    verifyBartenderRequest = uid => this.postNoResponse(`/users/bartenders/${uid}`);
}

export default AdminService;
