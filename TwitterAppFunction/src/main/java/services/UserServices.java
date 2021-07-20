package services;

import model.UserResponseModel;
import model.UsersDataStore;
import repository.UsersStoreImpl;
import utilities.DynamoDbClient;

public class UserServices {

    private static UsersStoreImpl usersStoreImpl;

    static {
        usersStoreImpl = new UsersStoreImpl(DynamoDbClient.getStandardClient());
    }

    public UserResponseModel loadUserDetails(String userId) {
        UsersDataStore usersDataStore = new UsersDataStore();
        usersDataStore.setUserId(userId);
        UsersDataStore loadedData = usersStoreImpl.load(usersDataStore);
        if(loadedData != null) {
            UserResponseModel userResponseModel = new UserResponseModel();
            userResponseModel.setUserId(loadedData.getUserId());
            userResponseModel.setUserFirstName(loadedData.getUserFirstName());
            userResponseModel.setUserLastName(loadedData.getUserLastName());
            userResponseModel.setEmail(loadedData.getEmail());
            return userResponseModel;
        }
        return null;
    }

    public String createUser(String userId,
                                                    String firstName,
                                                    String lastName,
                                                    String email,
                                                    String password) {
        UsersDataStore usersDataStore = new UsersDataStore();
        usersDataStore.setUserId(userId);
        usersDataStore.setUserFirstName(firstName);
        usersDataStore.setUserLastName(lastName);
        usersDataStore.setPassword(password);
        usersDataStore.setEmail(email);
        usersStoreImpl.save(usersDataStore);
        return "User created on twitter, please login to continue!";
    }
}
