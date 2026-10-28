
resource "kind_cluster" "terraform-cluster" {
  name = "terraform-sentinel-cluster"
  kind_config {
    kind        = "Cluster"
    api_version = "kind.x-k8s.io/v1alpha4"

    node {
      role = "control-plane"

    }

    node {
      role = "worker"
    }
    node {
      role = "worker"
    }
  }
}