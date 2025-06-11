import SwiftUI
import Shared
import KMPNativeCoroutinesAsync
import KMPObservableViewModelSwiftUI

struct MainScreen: View {

    var onAuctionCategoryClick: (Int32) -> Void
    
    @StateViewModel
    var viewModel = MainViewModel(
        repository: KoinDependencies().auctionsRepository
    )

    var body: some View {
        NavigationStack {
            VStack(alignment: .center, spacing: 0) {
                // Top bar
                TopBar(title: "Auctions")

                // Screen content based on state
                switch viewModel.state {
                case is MainScreenState.Loading:
                    Text("Loading data ...")
                        .frame(maxWidth: .infinity, maxHeight: .infinity)

                case let error as MainScreenState.Error:
                    Text(error.message)
                        .foregroundColor(.red)
                        .frame(maxWidth: .infinity, maxHeight: .infinity)

                case let content as MainScreenState.Content:
                    ScrollView {
                        LazyVStack(spacing: 8) {
                            ForEach(content.auctions, id: \.product.id) { auction in
                                NavigationLink(destination: AuctionScreen(
                                    auctionId: auction.product.id
                                )) {
                                    AuctionRow(
                                        auction: auction
                                    )
                                    .frame(maxWidth: .infinity)
                                    .padding(8)
                                }
                                .buttonStyle(PlainButtonStyle())
                            }
                        }
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .padding(.horizontal, 8)

                default:
                    EmptyView()
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
    }
}
