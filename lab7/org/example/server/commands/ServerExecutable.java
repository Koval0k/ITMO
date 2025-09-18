package org.example.server.commands;

import java.io.IOException;
import org.example.common.dto.*;

public interface ServerExecutable {
    public Response execute(Request request) throws IOException;

}