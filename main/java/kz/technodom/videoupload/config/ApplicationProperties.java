package kz.technodom.videoupload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Stands Videoupload.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final ApplicationProperties.VIDEOSDIR videosdir = new ApplicationProperties.VIDEOSDIR();
    private final ApplicationProperties.REMOTEDIR remotedir = new ApplicationProperties.REMOTEDIR();
    private final ApplicationProperties.ACTIVEDIRECTORY activedirectory = new ApplicationProperties.ACTIVEDIRECTORY();
    public ApplicationProperties.VIDEOSDIR getVideosdir() {
        return this.videosdir;
    }
    public ApplicationProperties.REMOTEDIR getRemotedir(){
        return this.remotedir;
    }
    public ApplicationProperties.ACTIVEDIRECTORY getActivedirectory(){
        return this.activedirectory;
    }

    public static class VIDEOSDIR{
        private String filepath;
        private String temporaryMountFolder;
        public VIDEOSDIR(){
        }
        public String getFilepath() {
            return filepath;
        }
        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public String getTemporaryMountFolder() {
            return temporaryMountFolder;
        }

        public void setTemporaryMountFolder(String temporaryMountFolder) {
            this.temporaryMountFolder = temporaryMountFolder;
        }
    }
    public static class REMOTEDIR{
        private String filepath;
        public REMOTEDIR(){
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }
    }

    public static class ACTIVEDIRECTORY{
        private String login;
        private String password;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
