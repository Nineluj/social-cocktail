# Social Cocktail

> Social media app based around cocktails. Find and share your favorite cocktails with your friends!

![Screenshot of details page](./assets/images/details.png)

Scales using AWS Auto Scaling Groups with an application load balancer. The application infrastructure is defined
with Terraform and AMIs are built using Packer. 

## Architecture
![Architecture Diagram](./assets/images/architecture.svg)

## Building

### Frontend

1. Enter the `client/` directory
2. (Optional) export `REACT_APP_BACKEND_HOST` to use a non-dev backend.
Will default to `localhost:8080`.
3. Run `npm run build`

Built files will be in `client/build/`

### Backend

1. Install `maven`. Check that you have it with `mvn --version`
2. Enter the `server/` directory
3. Run `mvn package`

Executable jar will be in `server/target/****.jar`

## Deploying

### Pre-deploy
Make sure that you have AWS configured locally. You should have the `aws`
cli executable and your credentials set up.

### Creating AMI for backend
1. Go to `infra/packer`
2. Run `packer build -on-error=ask packer-image.json` 
3. Take note of the ami id reported by packer

### Creating infrastructure
1. Go to `infra/terraform`
2. Create SSH keys so that you can connect to your SSH instances
(for troubleshooting) 
    ```shell script
    ssh-keygen -t rsa -m PEM
    ```
3. Rename the file `vars.tfvars.example` -> `vars.tfvars` and set the variables
4. Run `terraform plan -var-file=vars.tfvars -out <plan_name>` and then `terraform apply <plan_name>`


### Frontend
1. Make sure that you have built the frontend with the backend host environment
variable
2. Find the name of the S3 bucket generated from the previous step
3. Copy over the build files with
    ```shell script
    aws s3 cp client/build s3://<bucket_name> --recursive
    ```