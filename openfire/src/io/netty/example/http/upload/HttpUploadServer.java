
package io.netty.example.http.upload;

import com.example.constant.Constants;
import com.example.helper.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * A HTTP server showing how to use the HTTP multipart package for file uploads and decoding post data.
 */
public class HttpUploadServer {

    public HttpUploadServer() {
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap= new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HttpUploadServerInitializer());

            Channel channel = bootstrap.bind(Constants.SERVER_FILE_PORT).sync().channel();
            Logger.println(this,"Open your browser and navigate to http://localhost:" + Constants.SERVER_FILE_PORT + '/');
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpUploadServer().run();
    }
}
