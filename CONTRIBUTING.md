# Contribution Guidelines

For the contibutors of the Manhattan.

## Create an Issue

If you find a bug — _and you don’t know how to fix it_, **create an issue** on our Jira Backlog. Don't assign it to a sprint until it's review by the TL.

_or — even better_ fix it yourself!

### Jira workflow

#### Opening issues
1. Create an issue under the appropriate type
2. Set the required field, description,...
3. Assign the issue to yourself if it falls into your domain
4. The TechLead reviews the issuea and assigns it to a sprint and to the Subject Matter Expert (if wasn't set already)
5. The issue moves into the **Defined** state

#### Moving Issues around
* When an issue is fully described and has story points and an epic assigned to it, it's considered to be **Defined** so it must be in that column.
* When an assignee start working on a **Defined** issue, it's considered to be **Active** so it must be in that column.
  * Assignees must keep the **ACTUAL EFFORTS** field up the date on an **Active** issue. 
* When an issue is fixed and a PR is opened, it's considered to be **Implemented** so it must be in that column.
* When an issue is fixed and the associated PR is completed, it's considered to be **Closed** so it must be in that column.

#### Effort system
1 effort means the issue takes a whole workday to be completed.  
  * A workday depends on the IC themselves, a workday (without meeting and etc) might be 6 hours for you or 4 to someone else.  

0.5 effort means the issue needs half a workday to be completed.  
0.2 effort means the issue needs around a hour to be completed.  
0.4 effort means the issue needs around 2 hours to be completed.  
0.1 effort means the issue can be completed under 30 minutes.

## Create a Pull Request

If you know how to fix the issue yourself, submit a pull request with the proposed changes.

Here's what to do:

1. Clone the repository.
2. Create a new branch.
  1. Make sure you are on `master`
  2. `git pull`
  3. `git checkout -b <sprint-name>/<branchname>` So for example `setup-sprint/setting-up-github` 
4. Make your changes on the new branch.
5. Commit your changes in logical chunks.
6. Push your branch to remote. `git push`
7. Open a pull request.
    *  Make sure the title of your PR begins with the issues key! So for example `MAN-3 Create repository on Github` where `MAN-3` is the issue key.
