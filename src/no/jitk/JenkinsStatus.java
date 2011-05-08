package no.jitk;

public class JenkinsStatus {

    public enum Phase {
        STARTED(2),
        COMPLETED(4),
        FINISHED(6);
        private int code;

        Phase(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    ;

    public enum Status {
        SUCCESS(8),
        FAILURE(10),
        ABORTED(12);
        private int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    ;

    @Override
    public String toString() {
        return "JenkinsStatus{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", build=" + build +
                '}';
    }

    private String name;
    private String url;
    private Build build;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Build {

        private int number;
        private Phase phase;
        private Status status;
        private String url;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public Phase getPhase() {
            return phase;
        }

        public void setPhase(Phase phase) {
            this.phase = phase;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Build{" +
                    "number=" + number +
                    ", phase=" + phase +
                    ", status=" + status +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
