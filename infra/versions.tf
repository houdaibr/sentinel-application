terraform {
    required_version = "~> 1.9"
    required_providers {
        docker = {
            source  = "kreuzwerker/docker"
            version = "~> 3.0"
        }
        kind = {
            source  = "tehcyx/kind"
            version = "0.11.0"
        }
    }


}
provider kind {}

provider docker {}