package com.emmettito.tickettorideserver.user;

import com.emmettito.models.CommandModels.UserCommand;
import com.emmettito.models.Result;

public class LoginCommand implements IUserCommand{
    @Override
    public Result execute(UserCommand obj) throws Exception {
        return new Result();
    }
}
