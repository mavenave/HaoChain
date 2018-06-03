import com.hao.HaoChain.{Block, Blockchain}


class HaoChain extends Blockchain {
  difficulty = 6
}

object HaoChain {

  def main(args: Array[String]): Unit = {
    val genesisHash = "A6D72BAA3DB900B03E70DF880E503E9164013B4D9A470853EDC115776323A098"
    var hash: String = ""
    var blockchain: HaoChain = new HaoChain()

    var startTime: Long = System.currentTimeMillis()
    blockchain.blocks.append(new Block(genesisHash, "First block"))
    hash = blockchain.blocks(0).mineBlock(blockchain.difficulty)
    var endTime: Long = System.currentTimeMillis()
    println("Block " + hash + "mined in: " + (endTime - startTime).toString)

    startTime = System.currentTimeMillis()
    blockchain.blocks.append(
      new Block(blockchain.blocks(blockchain.blocks.length - 1).hash, "Second block"))
    blockchain.blocks(1).mineBlock(blockchain.difficulty)
    endTime = System.currentTimeMillis()
    println("Block " + hash + "mined in: " + (endTime - startTime).toString)

    startTime = System.currentTimeMillis()
    blockchain.blocks.append(
      new Block(blockchain.blocks(blockchain.blocks.length - 1).hash, "Third block"))
    blockchain.blocks(2).mineBlock(blockchain.difficulty)
    endTime = System.currentTimeMillis()
    println("Block " + hash + "mined in: " + (endTime - startTime).toString)
  }
}

