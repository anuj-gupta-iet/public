### How To commit a Project in this Repo
* create a folder in your local e.g. E:\github-repo
* open terminal/gitbash inside that folder and execute 'git init' command
* now execute command 'git remote add origin https://github.com/anuj-gupta-iet/public.git'
* execute command 'git pull origin master'. this will sync your local repo with remote repo
* now add your project folder by copy pasting it e.g. folder 'E:\github-repo\social-media-website'
* add this folder and all its files to git by command 'git add'
* check all files are ready to be committed by using command 'git status'. files will be shown in green
* commit to git local repo using command 'git commit -m "commit message"'
* now execute command 'git push origin master'
* above command will ask for username/password. put username as 'anuj-gupta-iet'
* for password login to github account. click on top right profile icon->settings->developer settings->personal access tokens->generate new token. refer this page for reference https://ginnyfahs.medium.com/github-error-authentication-failed-from-command-line-3a545bfd0ca8