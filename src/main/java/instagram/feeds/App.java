package instagram.feeds;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

/**
 * Hello world!
 *
 */
public class App {

	private static final String PASS_WORD = " ";
	private static final String USER_NAME = "user.name.tenzin";

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Instagram4j instagram = Instagram4j.builder().username(USER_NAME).password(PASS_WORD).build();
		instagram.setup();
		instagram.login();
		InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(USER_NAME));
		System.out.println("Number of my followers : " + userResult.getUser().getFollower_count());
		System.out.println("Number of my followings : " + userResult.getUser().getFollowing_count());
		InstagramGetUserFollowersResult followers = instagram
				.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));
		List<InstagramUserSummary> users = followers.getUsers();
		System.out.println("size :" + users.size());
		int count = 1;
		for (InstagramUserSummary user : users) {
			System.out.println("UserName " + count + " :  " + user.getUsername());
			count++;
			InstagramSearchUsernameResult userResultNew = instagram
					.sendRequest(new InstagramSearchUsernameRequest(user.getUsername()));
			InstagramGetUserFollowersResult followersNew = instagram
					.sendRequest(new InstagramGetUserFollowersRequest(userResultNew.getUser().getPk()));
			List<InstagramUserSummary> usersNew = followersNew.getUsers();
			for (InstagramUserSummary userSummary : usersNew) {
				System.out.println(user.getUsername() + " has this follower :" + userSummary.getUsername());
			}
		}
	}
}
